import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class MainTest:WordSpec ({
    "a red traffic light" should  ({
        val redTrafficLight = Red(fixture = 1234)
        "when changed once should turn red/amber" {
            val newTrafficLight = changeColors(redTrafficLight,1)
            newTrafficLight.shouldBeTypeOf<RedAmber>()
            newTrafficLight.fixture shouldBe 1234
        }
        "when changed two times turns green" {
            val newTrafficLight = changeColors(redTrafficLight,2)
            newTrafficLight.shouldBeTypeOf<Green>()
            newTrafficLight.fixture shouldBe 1234
        }
        "when changed three times turns amber" {
            val newTrafficLight = changeColors(redTrafficLight,3)
            newTrafficLight.shouldBeTypeOf<Amber>()
            newTrafficLight.fixture shouldBe 1234
        }
        "when changed four times turns red" {
            val newTrafficLight = changeColors(redTrafficLight,4)
            newTrafficLight.shouldBeTypeOf<Red>()
            newTrafficLight.fixture shouldBe 1234
        }
    })
    "a red traffic light that can sometimes go straight to green" should ({
        "when changed once should not skip amber if flag not set" {
            val redTrafficLight = RedVersion2(fixture = 1234, shouldSkipAmber = false)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.RedAmberOption>()
        }
        "when changed once should skip amber if flag is set" {
            val redTrafficLight = RedVersion2(fixture = 1234, shouldSkipAmber = true)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.GreenOption>()
        }
        "when changed two times turns green if skip amber flag not set" {
            val redTrafficLight = RedVersion2(fixture = 1234, shouldSkipAmber = false)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Green>()
        }
        "when changed two times turns amber if skip amber flag set" {
            val redTrafficLight = RedVersion2(fixture = 1234, shouldSkipAmber = true)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Amber>()
        }
        "when fixture is zero the traffic light is always broken" {
            val redTrafficLight = RedVersion2(fixture = 0, shouldSkipAmber = true)
            val newTrafficLight = changeColors(redTrafficLight,2)
            newTrafficLight.shouldBeTypeOf<Broken>()
            newTrafficLight.fixture shouldBe 0
            newTrafficLight.reason shouldBe "Fixture 0 doesn't work"
        }

    })
})