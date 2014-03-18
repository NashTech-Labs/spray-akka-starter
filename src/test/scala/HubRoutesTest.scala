import com.knoldus.hub.HubRoutes
import spray.testkit.Specs2RouteTest
import org.specs2.mutable.Specification

class HubRoutesTest extends Specification with Specs2RouteTest with HubRoutes {

  def actorRefFactory = system
  implicit val routeTestTimeout = RouteTestTimeout(timeout.duration)

  "HubRouter" should {

    "Testing Hub Router for happy flow" in {
      Get("/module/BANG") ~> hubRoutes ~> check {
        responseAs[String] must contain("Hello")
      }
    }
    "Testing Hub Router for alternate flow" in {
      Get("/module/anything") ~> hubRoutes ~> check {
        responseAs[String] must contain("Bad")
      }
    }
     "Testing Hub Router for POST flow" in {
      Post("/module", "Some Value") ~> hubRoutes ~> check {
        responseAs[String] must contain("Currently I cannot")
      }
    }
  }
}