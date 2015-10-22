# Canvas Frenzy

A simple experiment with the HTML5 canvas, timers and some keyboard and mouse interaction.

With some inspiration taken from [Hands-on Scala.js](http://lihaoyi.github.io/hands-on-scala-js/).

## Development

- Launch `sbt`.

- For continuous compilation of your changes type `~fastOptJS`.

- Then open the following URL within your favorite browser: [http://localhost:12345/target/scala-2.11/classes/index-dev.html](http://localhost:12345/target/scala-2.11/classes/index-dev.html)

## Deployment

- Launch `sbt` and type `fullOptJS`.

- Then extract the following files:
```
target/scala-2.11/
  +- canvas-frenzy-opt.js
  +- canvas-frenzy-jsdeps.min.js
  +- classes/
          +- index.html
          +- style.css
```

- Finally open the `index.html` in the browser of your choice.

## How does it work?

Left-click and move the mouse to draw at any time. Also try out to resize the browser window.

You can use the following key commands:

| Key    | Action                        |
| ------ | ----------------------------- |
| Space  | stops auto-draw               |
| Escape | clears the canvas             |
| Up     | increases the auto-draw speed |
| Down   | decreases the auto-draw speed |

Have a nice frenzy!
