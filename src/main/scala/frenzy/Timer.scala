package frenzy

import org.scalajs.dom

class Timer(var interval: Int, action: => Unit) {
  val minInterval = 1
  val maxInterval = 2048
  val inc = 2
  private var timerId: Option[Int] = None

  def incr() = {
    if (interval <= maxInterval) interval *= inc
    updateInterval()
  }

  def decr() = {
    if (interval > minInterval) interval /= inc
    updateInterval()
  }

  def toggle() = timerId match {
    case None => start()
    case Some(id) => dom.clearInterval(id)
  }

  private def updateInterval() = timerId.foreach( id => {
    dom.clearInterval(id)
    timerId = Some(dom.setInterval(() => action, interval))
    println(s"timer interval: $interval")
  })

  def start() =
    timerId = Some(dom.setInterval(() => action, interval))
}
