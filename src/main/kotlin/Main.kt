interface TypeOfTrafficLight {
    fun changeColour():TypeOfTrafficLight
}

//This applies changes to a traffic light by the number of times specified by numberOfChanges
fun changeColors(typeOfTrafficLight: TypeOfTrafficLight, numberOfChanges:Int):TypeOfTrafficLight =
     (1..numberOfChanges).fold(typeOfTrafficLight){ tls, _ -> tls.changeColour() }

class RedTrafficLight(val serialNumber:Int):TypeOfTrafficLight {
    override fun changeColour():RedOutput =
        if (serialNumber != 0)
            RedOutput.RedAmberOption(serialNumber)
        else
            RedOutput.BrokenOption(serialNumber, "Light with SerialNumber 0 doesn't work")

    override fun toString() = "I am Red"
}

sealed class RedOutput(val typeOfTrafficLight: TypeOfTrafficLight):TypeOfTrafficLight {
    class RedAmberOption( serialNumber:Int):RedOutput(RedAmberTrafficLight(serialNumber))
    class BrokenOption( serialNumber:Int, reason:String):RedOutput(BrokenTrafficLight(serialNumber, reason))
    override fun changeColour(): TypeOfTrafficLight = this.typeOfTrafficLight.changeColour()
}

class RedAmberTrafficLight(val serialNumber:Int):TypeOfTrafficLight {
    override fun changeColour():GreenTrafficLight = GreenTrafficLight(serialNumber)
    override fun toString() = "I am Red and Amber"
}

class GreenTrafficLight(val serialNumber:Int):TypeOfTrafficLight {
    override fun changeColour():AmberTrafficLight = AmberTrafficLight(serialNumber)
    override fun toString() = "I am Green"
}

class AmberTrafficLight(val serialNumber:Int):TypeOfTrafficLight {
    override fun changeColour():RedTrafficLight = RedTrafficLight(serialNumber)
    override fun toString() = "I am Amber"
}

//Broken is a special case where the actual traffic light can't change colour, e.g. bulbs not working or no power.
class BrokenTrafficLight(val serialNumber:Int, val reason:String):TypeOfTrafficLight {
    override fun changeColour():BrokenTrafficLight = BrokenTrafficLight(serialNumber, "Still broken")
    override fun toString() = "I am Broken"
}
