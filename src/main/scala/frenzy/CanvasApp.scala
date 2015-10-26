package frenzy

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, document, html}
import org.scalajs.dom.ext.KeyCode
import scalatags.JsDom.all._
import scala.util.Random

object CanvasApp extends JSApp {
  val margin = 50
  val penWidth = 20
  val defaultTimerInterval = 64

  def main(): Unit = {
    val container = div.render
    document.body.appendChild(container)

    val canvas = dom.document.createElement("canvas").asInstanceOf[html.Canvas]
    container.appendChild(canvas)
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val docEl = dom.document.documentElement

    def dims = Dimension(docEl.clientWidth, docEl.clientHeight)

    canvas.style.left = s"${margin}px"
    canvas.style.top = s"${margin}px"
    canvas.style.position = "absolute"

    def canvasWidth = dims.w - 2 * margin
    def canvasHeigth = dims.h - 2 * margin

    def resize(): Unit = {
      canvas.width = canvasWidth
      canvas.height = canvasHeigth
    }
    resize()

    dom.window.onresize = { (evt: dom.Event) => {
      resize()
      clear()
      println(s"resized: ${dims}")
    } }

    def clear(): Unit = {
      canvas.width = canvasWidth
      ctx.fillStyle = "silver"
      ctx.fillRect(0, 0, dims.w, dims.h)
    }

    def randomColor(alpha: Double = Random.nextDouble) = {
      def rnd = Random.nextInt(256)
      s"rgba($rnd, $rnd, $rnd, ${alpha})"
    }

    var lastDim = Dimension.random(dims)

    def draw(): Unit = {
      // for slowly shifting between randomly sized shapes
      def randomDimSmoothed(scale: Int = 1): Dimension = {
        val newDim = Dimension.random(Dimension(dims.w/scale, dims.h/scale))
        val factor = 0.1
        def interpolate(a: Int, b: Int, factor: Double): Int = {
          require(0 <= factor && factor <= 1)
          ((1 - factor) * a + factor * b).toInt
        }
        Dimension(
          interpolate(newDim.w, lastDim.w, factor),
          interpolate(newDim.h, lastDim.h, factor)
        )
      }
      def drawRndCircleAt(pos: Point) = {
        val rad = randomDimSmoothed(20).w
        ctx.beginPath()
        ctx.arc(pos.x, pos.y, rad, 0, 360)
        ctx.fill()
      }
      def drawRndRectAt(pos: Point) = {
        val dim = randomDimSmoothed(10)
        ctx.fillRect(pos.x, pos.y, dim.w, dim.h)
      }
      def randomPoint = Point.random(dims)

      ctx.fillStyle = randomColor()
      Random.nextInt(2) match {
        case 0 => drawRndCircleAt(randomPoint)
        case 1 => drawRndRectAt(randomPoint)
      }
    }


    def mousePos(evt: dom.MouseEvent) = {
      val rect = canvas.getBoundingClientRect
      Point(evt.clientX - rect.left, evt.clientY - rect.top)
    }

    var interactiveDrawIsActive = false
    var penPos = Point(-1, -1)
    var col = randomColor()

    canvas.onmousedown = { (evt: dom.MouseEvent) => {
      interactiveDrawIsActive = true
      penPos = mousePos(evt)
      ctx.moveTo(penPos.x, penPos.y)
    } }

    canvas.onmousemove = { (evt: dom.MouseEvent) => if (interactiveDrawIsActive) {
      val oldPos = penPos
      val oldCol = col
      penPos = mousePos(evt)
      col = randomColor()

      val gradient = ctx.createLinearGradient(oldPos.x, oldPos.y, penPos.x, penPos.y)
      gradient.addColorStop(0, oldCol)
      gradient.addColorStop(1, col)

      ctx.strokeStyle = gradient
      ctx.lineWidth = penWidth
      ctx.beginPath()
      ctx.moveTo(oldPos.x, oldPos.y)
      ctx.lineTo(penPos.x, penPos.y)
      ctx.stroke()
    } }

    canvas.onmouseup = { (evt: dom.MouseEvent) => {
      interactiveDrawIsActive = false
    } }

    var timer = new Timer(defaultTimerInterval, draw)

    dom.document.onkeyup = { (evt: dom.KeyboardEvent) => evt.keyCode match {
      case KeyCode.space =>  timer.toggle()
      case KeyCode.up =>     timer.decr()
      case KeyCode.down =>   timer.incr()
      case KeyCode.escape => clear()
      case _ => ()
    }}

    clear()
    timer.start()

    println("UI was set up.")
  }
}
