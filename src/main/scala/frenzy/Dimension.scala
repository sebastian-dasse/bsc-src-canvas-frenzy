package frenzy

import scala.util.Random

case class Dimension(w: Int, h: Int) {
  def +(n: Int): Dimension = Dimension(w + n, h + n)
  def +(other: Dimension): Dimension = Dimension(w + other.w, h + other.h)
  def -(n: Int): Dimension = this + (-n)
  def -(other: Dimension): Dimension = Dimension(w - other.w, h - other.h)
  def *(n: Int): Dimension = Dimension(w * n, h * n)
  def /(n: Int): Dimension = Dimension(w / n, h / n)
}

object Dimension {
  def random(d: Dimension): Dimension =
    Dimension(Random.nextInt(d.w), Random.nextInt(d.h))
}
