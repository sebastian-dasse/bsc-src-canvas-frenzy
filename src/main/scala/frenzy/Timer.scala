package frenzy

import org.scalajs.dom

class Timer(var interval: Int, action: => Unit) {
  private var timerId = -1
  val inc = 2

  def incr() = {
    if (interval <= 2048) interval *= inc
    updateInterval()
  }

  def decr() = {
    if (interval > 1) interval /= inc
    updateInterval()
  }

  private def updateInterval() = {
    dom.clearInterval(timerId)
    timerId = dom.setInterval(() => action, interval)
    println(s"timer interval: $interval")
  }

  def start() =
    dom.setInterval(() => action, interval)
}
