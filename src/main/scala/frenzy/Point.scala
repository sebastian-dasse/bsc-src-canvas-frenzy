package frenzy

import scala.util.Random

case class Point(x: Int, y: Int)

object Point{
  def apply(x: Double, y: Double): Point =
    Point(x.toInt, y.toInt)

  def random(d: Dimension): Point =
    Point(Random.nextInt(d.w), Random.nextInt(d.h))
}
