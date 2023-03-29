import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class MainTest:WordSpec ({
    "a red traffic light" should  ({

        val redTrafficLight = RedTrafficLight(serialNumber = 1234)

        "when changed once should turn red/amber" {
            val newTrafficLight = changeColors(redTrafficLight,1)
            newTrafficLight.shouldBeTypeOf <RedOutput.RedAmberOption>()
            newTrafficLight.typeOfTrafficLight.shouldBeTypeOf<RedAmberTrafficLight>()
            (newTrafficLight.typeOfTrafficLight as RedAmberTrafficLight).serialNumber shouldBe 1234
        }
        "when changed two times turns green" {
            val newTrafficLight = changeColors(redTrafficLight,2)
            newTrafficLight.shouldBeTypeOf<GreenTrafficLight>()
            newTrafficLight.serialNumber shouldBe 1234
        }
        "when changed three times turns amber" {
            val newTrafficLight = changeColors(redTrafficLight,3)
            newTrafficLight.shouldBeTypeOf<AmberTrafficLight>()
            newTrafficLight.serialNumber shouldBe 1234
        }
        "when changed four times turns red" {
            val newTrafficLight = changeColors(redTrafficLight,4)
            newTrafficLight.shouldBeTypeOf<RedTrafficLight>()
            newTrafficLight.serialNumber shouldBe 1234
        }
    })
    "a red traffic light with serialnumber zero" should  ({

        val redTrafficLight = RedTrafficLight(serialNumber = 0)

        "when changed once should produce a broken traffic light" {
            val newTrafficLight = changeColors(redTrafficLight,1)
            newTrafficLight.shouldBeTypeOf <RedOutput.BrokenOption>()
            newTrafficLight.typeOfTrafficLight.shouldBeTypeOf<BrokenTrafficLight>()
            (newTrafficLight.typeOfTrafficLight as BrokenTrafficLight).serialNumber shouldBe 0
            (newTrafficLight.typeOfTrafficLight as BrokenTrafficLight).reason shouldBe "Light with SerialNumber 0 doesn't work"
        }
        "when changed twice should remain broken" {
            val newTrafficLight = changeColors(redTrafficLight,2)
            newTrafficLight.shouldBeTypeOf <BrokenTrafficLight>()
            newTrafficLight.serialNumber shouldBe 0
            newTrafficLight.reason shouldBe "Still broken"
        }
    })
})