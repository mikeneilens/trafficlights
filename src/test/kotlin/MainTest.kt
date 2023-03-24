import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class MainTest:WordSpec ({
    "a red traffic light" should  ({
        val redTrafficLight = Red
        "when changed once should turn red/amber" {
            changeColors(redTrafficLight,1).shouldBeTypeOf<RedAmber>()
        }
        "when changed two times turns green" {
            changeColors(redTrafficLight,2).shouldBeTypeOf<Green>()
        }
        "when changed three times turns amber" {
            changeColors(redTrafficLight,3).shouldBeTypeOf<Amber>()
        }
        "when changed four times turns red" {
            changeColors(redTrafficLight,4).shouldBeTypeOf<Red>()
        }
    })
    "a red traffic light that can sometimes go straight to green" should ({
        "when changed once should not skip amber if flag not set" {
            val redTrafficLight = Red2(shouldSkipAmber = false)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.RedAmberOption>()
        }
        "when changed once should skip amber if flag is set" {
            val redTrafficLight = Red2(shouldSkipAmber = true)
            changeColors(redTrafficLight,1).shouldBeTypeOf <RedOutput.GreenOption>()
        }
        "when changed two times turns green if skip amber flag not set" {
            val redTrafficLight = Red2(shouldSkipAmber = false)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Green>()
        }
        "when changed two times turns amber if skip amber flag set" {
            val redTrafficLight = Red2(shouldSkipAmber = true)
            changeColors(redTrafficLight,2).shouldBeTypeOf<Amber>()
        }

    })
})