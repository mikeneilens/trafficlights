import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class MainTest:WordSpec ({
    "a red traffic light" should  ({
        val redTrafficLight = Red(serialNumber = 1234)
        "when changed once should turn red/amber" {
            val newTrafficLight = changeColors(redTrafficLight,1)
            newTrafficLight.shouldBeTypeOf<RedAmber>()
            newTrafficLight.serialNumber shouldBe 1234
        }
        "when changed two times turns green" {
            val newTrafficLight = changeColors(redTrafficLight,2)
            newTrafficLight.shouldBeTypeOf<Green>()
            newTrafficLight.serialNumber shouldBe 1234
        }
        "when changed three times turns amber" {
            val newTrafficLight = changeColors(redTrafficLight,3)
            newTrafficLight.shouldBeTypeOf<Amber>()
            newTrafficLight.serialNumber shouldBe 1234
        }
        "when changed four times turns red" {
            val newTrafficLight = changeColors(redTrafficLight,4)
            newTrafficLight.shouldBeTypeOf<Red>()
            newTrafficLight.serialNumber shouldBe 1234
        }
    })
    "a red traffic light that can sometimes go straight to green" should ({
        "when changed once should not skip amber if flag not set" {
            val redTrafficLight = RedVersion2(serialNumber = 1234, shouldSkipAmber = false)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.RedAmberOption>()
        }
        "when changed once should skip amber if flag is set" {
            val redTrafficLight = RedVersion2(serialNumber = 1234, shouldSkipAmber = true)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.GreenOption>()
        }
        "when changed two times turns green if skip amber flag not set" {
            val redTrafficLight = RedVersion2(serialNumber = 1234, shouldSkipAmber = false)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Green>()
        }
        "when changed two times turns amber if skip amber flag set" {
            val redTrafficLight = RedVersion2(serialNumber = 1234, shouldSkipAmber = true)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Amber>()
        }
        "when serial number is zero the traffic light is always broken" {
            val redTrafficLight = RedVersion2(serialNumber = 0, shouldSkipAmber = true)
            val newTrafficLight = redTrafficLight.changeColour()
            newTrafficLight.shouldBeTypeOf<RedOutput.BrokenOption>()
            newTrafficLight.trafficLightState.shouldBeTypeOf<Broken>()
            (newTrafficLight.trafficLightState as Broken).run {
                serialNumber shouldBe 0
                reason shouldBe "Light with SerialNumber 0 doesn't work"
            }

            val trafficLightAfterSecondChange = newTrafficLight.changeColour()
            trafficLightAfterSecondChange.shouldBeTypeOf<Broken>()
            trafficLightAfterSecondChange.reason shouldBe "Still broken"
        }

    })
})