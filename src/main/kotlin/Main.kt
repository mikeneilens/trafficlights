interface TrafficLightState {
    fun changeColour():TrafficLightState
}

//This applies changes to a traffic light by the number of times specified by numberOfChanges
fun changeColors(trafficLightState: TrafficLightState, numberOfChanges:Int):TrafficLightState =
     (1..numberOfChanges).fold(trafficLightState){tls, _ -> tls.changeColour() }

class Red:TrafficLightState {
    override fun changeColour():RedAmber = RedAmber()
    override fun toString() = "I am Red"
}

class RedAmber:TrafficLightState {
    override fun changeColour():Green = Green()
    override fun toString() = "I am Red and Amber"
}

class Green:TrafficLightState {
    override fun changeColour():Amber = Amber()
    override fun toString() = "I am Green"
}

class Amber:TrafficLightState {
    override fun changeColour():Red = Red()
    override fun toString() = "I am Amber"
}


//If Red had more than one possible output then we could use something like this
//n.b. Once RedVersion2 has been changed several times it turns back into a standard red traffic light!
class RedVersion2(val shouldSkipAmber:Boolean):TrafficLightState {
    override fun changeColour():RedOutput = if (shouldSkipAmber) RedOutput.GreenOption() else RedOutput.RedAmberOption()
    override fun toString() = "I am Red but can turn to red/amber or go straight to green"
}

sealed class RedOutput(val trafficLightState: TrafficLightState):TrafficLightState {
    class RedAmberOption:RedOutput(RedAmber())
    class GreenOption:RedOutput(Green())
    override fun changeColour(): TrafficLightState = this.trafficLightState.changeColour()
}

