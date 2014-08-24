package com.hanh

import scala.concurrent.duration._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl.Flow
import akka.stream.actor._

case class Tick()

class PingActor extends ActorPublisher[Int] {
  implicit val ec = context.dispatcher
  context.system.scheduler.schedule(0.second, 1.second, self, Tick)
  var cnt = 0
  
  def receive = {
    case Tick =>
      if (isActive && totalDemand > 0) {
        onNext(cnt)
        cnt += 1
      }
  }
}

class CalcConsumer extends ActorSubscriber {
  val requestStrategy = OneByOneRequestStrategy 
  def receive = {
    case ActorSubscriberMessage.OnNext(m: Int) =>
      println(s">> $m")
    case x => println(x)
  }
}

class Test2 extends Actor {
	  val settings = MaterializerSettings()
	  implicit val materializer = FlowMaterializer(settings)
		val t = T(1)
		println(s"Hello $t")
		
		val p = context.actorOf(Props[PingActor])
		val c = context.actorOf(Props[CalcConsumer])
		Flow(ActorPublisher[Int](p))
		.map(i => i * 2 + 1)
		.produceTo(ActorSubscriber(c))
		
		val receive = Actor.emptyBehavior
}

object Test2 {
	def main(args: Array[String]) {
	  val system = ActorSystem()
	  val a = system.actorOf(Props[Test2])
	}
}