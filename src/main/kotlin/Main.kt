interface TrafficLightState {
    fun changeColour():TrafficLightState
}

//This applies changes to a traffic light by the number of times specified by numberOfChanges
fun changeColors(trafficLightState: TrafficLightState, numberOfChanges:Int):TrafficLightState =
     (1..numberOfChanges).fold(trafficLightState){tls, _ -> tls.changeColour() }

class Red(val serialNumber:Int):TrafficLightState {
    override fun changeColour():RedAmber = RedAmber(serialNumber)
    override fun toString() = "I am Red"
}

class RedAmber(val serialNumber:Int):TrafficLightState {
    override fun changeColour():Green = Green(serialNumber)
    override fun toString() = "I am Red and Amber"
}

class Green(val serialNumber:Int):TrafficLightState {
    override fun changeColour():Amber = Amber(serialNumber)
    override fun toString() = "I am Green"
}

class Amber(val serialNumber:Int):TrafficLightState {
    override fun changeColour():Red = Red(serialNumber)
    override fun toString() = "I am Amber"
}


//If Red had more than one possible output then we could use something like this
//n.b. Once RedVersion2 has been changed several times it turns back into a standard red traffic light!
class RedVersion2(val serialNumber:Int, val shouldSkipAmber:Boolean):TrafficLightState {
    override fun changeColour():RedOutput =
        if (serialNumber == 0) RedOutput.BrokenOption(serialNumber, "Light with SerialNumber 0 doesn't work")
        else if (shouldSkipAmber) RedOutput.GreenOption(serialNumber)
        else RedOutput.RedAmberOption(serialNumber)
    override fun toString() = "I am Red but can turn to red/amber or go straight to green"
}

sealed class RedOutput(val trafficLightState: TrafficLightState):TrafficLightState {
    class RedAmberOption( serialNumber:Int):RedOutput(RedAmber(serialNumber))
    class GreenOption( serialNumber:Int):RedOutput(Green(serialNumber))
    class BrokenOption( serialNumber:Int, val reason:String):RedOutput(Broken(serialNumber, reason))
    override fun changeColour(): TrafficLightState = this.trafficLightState.changeColour()
}

//Broken is a special case where the actual traffic light can't change colour, e.g. bulbs not working or no power.
class Broken(val serialNumber:Int, val reason:String):TrafficLightState {
    override fun changeColour():Broken = Broken(serialNumber, "Still broken")
    override fun toString() = "I am Broken"
}
