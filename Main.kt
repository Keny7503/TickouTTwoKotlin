fun main(args: Array<String>) {
    var flipEachGame= true
    do {
        flipEachGame =!flipEachGame
        println(startGame(flipEachGame))
    }while (inputWithCheck(listOf("y","n"))=="y")

}
fun startGame(verticalFisrt: Boolean=true):String{
    val data= mutableListOf(Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY)
    printBoard(data)
    var flip =verticalFisrt
    var lastTurn=0
    while(true){
        flip=!flip
        val playerchoose = playerTurn(flip,data,lastTurn)
        lastTurn = playerchoose.first+1
        data[playerchoose.first]=playerchoose.second
        printBoard(data)
        if (isWinning(data)){
            val playerString = if (flip)    "Vertical" else "Horizontal"
            return "$playerString win. Continue? (y/n):"
        }
    }

}
fun playerTurn(vertical:Boolean=true, data:List<Cell>, lastTurn:Int=0):Pair<Int,Cell>{
    val avalableSlot= mutableListOf<Int>()
    data.forEachIndexed{id,value ->
        run {
            when (value) {
                Cell.HORRIZONTAL -> if(vertical)    avalableSlot.add(id+1)
                Cell.VERTICAL -> if(!vertical)    avalableSlot.add(id+1)
                Cell.EMPTY -> avalableSlot.add(id+1)
                else -> {}
            }
        }
    }
    if(lastTurn!=0) avalableSlot.remove(lastTurn)
    val playerString = if (vertical)    "Vertical" else "Horizontal"
    println("$playerString turn, choose your move: $avalableSlot")
    val choose = inputWithCheck(avalableSlot.map { "$it" }).toInt()
    val cellChange = when (data[choose-1]) {
        Cell.HORRIZONTAL -> Cell.PLUS
        Cell.VERTICAL -> Cell.PLUS
        Cell.EMPTY -> if(vertical) Cell.VERTICAL else Cell.HORRIZONTAL
        Cell.PLUS ->    Cell.PLUS
    }
    return Pair(choose-1,cellChange)
    //continue

}
fun inputWithCheck(mustBe:List<String>):String{
    var input= readLine()
    while(!mustBe.contains(input)){
        println("You must enter $mustBe")
        input = readLine()
    }
    return input!!
}

fun printBoard(data:List<Cell>){
    val s ="          ".toMutableList()
    data.forEachIndexed { id,value  ->
        run {
            when(value){
                Cell.HORRIZONTAL -> s[id+1] ='—'
                Cell.VERTICAL -> s[id+1] ='|'
                Cell.PLUS -> s[id+1] ='+'
                else -> s[id+1] =' '
            }
        }
    }
    println(" ─────\n| ${s[7]}${s[8]}${s[9]} |\n| ${s[4]}${s[5]}${s[6]} |\n| ${s[1]}${s[2]}${s[3]} |\n ─────")
}

fun isWinning(data: List<Cell>):Boolean{
    var winning =false
    when(true){
        (data[6]== Cell.PLUS) ->{if((data[7]== Cell.PLUS&&data[8]== Cell.PLUS)||(data[3]== Cell.PLUS&&data[0]== Cell.PLUS)) winning=true}
        (data[4]== Cell.PLUS) ->{if((data[7]== Cell.PLUS&&data[1]== Cell.PLUS)||(data[5]== Cell.PLUS&&data[3]== Cell.PLUS)||(data[8]== Cell.PLUS&&data[0]== Cell.PLUS)||(data[6]== Cell.PLUS&&data[2]== Cell.PLUS)) winning=true}
        (data[2]== Cell.PLUS) ->{if((data[5]== Cell.PLUS&&data[8]== Cell.PLUS)||(data[1]== Cell.PLUS&&data[0]== Cell.PLUS)) winning=true}
        else ->{}
    }
    return winning //temporary
}

enum class Cell{EMPTY,VERTICAL,HORRIZONTAL,PLUS}