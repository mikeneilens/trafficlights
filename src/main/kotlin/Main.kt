interface TrafficLightState {
    fun changeColour():TrafficLightState
}

//This applies changes to a traffic light by the number of times specified by numberOfChanges
fun changeColors(trafficLightState: TrafficLightState, numberOfChanges:Int):TrafficLightState =
     (1..numberOfChanges).fold(trafficLightState){tls, _ -> tls.changeColour() }

class Red(val fixture:Int):TrafficLightState {
    override fun changeColour():RedAmber = RedAmber(fixture)
    override fun toString() = "I am Red"
}

class RedAmber(val fixture:Int):TrafficLightState {
    override fun changeColour():Green = Green(fixture)
    override fun toString() = "I am Red and Amber"
}

class Green(val fixture:Int):TrafficLightState {
    override fun changeColour():Amber = Amber(fixture)
    override fun toString() = "I am Green"
}

class Amber(val fixture:Int):TrafficLightState {
    override fun changeColour():Red = Red(fixture)
    override fun toString() = "I am Amber"
}


//If Red had more than one possible output then we could use something like this
//n.b. Once RedVersion2 has been changed several times it turns back into a standard red traffic light!
class RedVersion2(val fixture:Int, val shouldSkipAmber:Boolean):TrafficLightState {
    override fun changeColour():RedOutput =
        if (fixture == 0) RedOutput.BrokenOption(fixture, "Fixture 0 doesn't work")
        else if (shouldSkipAmber) RedOutput.GreenOption(fixture)
        else RedOutput.RedAmberOption(fixture)
    override fun toString() = "I am Red but can turn to red/amber or go straight to green"
}

sealed class RedOutput(val trafficLightState: TrafficLightState):TrafficLightState {
    class RedAmberOption(val fixture:Int):RedOutput(RedAmber(fixture))
    class GreenOption(val fixture:Int):RedOutput(Green(fixture))
    class BrokenOption(val fixture:Int, val reason:String):RedOutput(Broken(fixture, reason))
    override fun changeColour(): TrafficLightState = this.trafficLightState.changeColour()
}

class Broken(val fixture:Int, val reason:String):TrafficLightState {
    override fun changeColour():Broken = Broken(fixture, "Still broken")
    override fun toString() = "I am Broken"
}

