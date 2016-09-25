package io.haitch

import sbt.Keys._
import sbt._
import sbt.complete.DefaultParsers._


object SbtRocks extends AutoPlugin {

  val modelPath = "/models"
  val luaModPath =  modelPath + "/lua_modules"
  val setPath = modelPath + "/set_path.lua"

  object autoImport {
    val rocks = inputKey[Unit]("Run Luarocks")
    val th = inputKey[Unit]("Execute script")


  }

  import autoImport._


  override lazy val projectSettings = {
    Seq(
      rocks := {
        val bd = baseDirectory.value
        val md = bd + "/models"
        val lm = md + "/lua_modules"
        val setPath = md + "/set_path.lua"
        val args = spaceDelimited("").parsed

        val dir = new File(lm)
        dir.mkdirs()
        // set_paths.lua
        val luaContentSetPath =
        """
          |local version = _VERSION:match("%d+%.%d+")
          |package.path = 'lua_modules/share/lua/' .. version .. '/?.lua;lua_modules/share/lua/' .. version .. '/?/init.lua' .. package.path
          |package.cpath = 'lua_modules/lib/lua/' .. version .. '/?.so' .. package.path
          """.stripMargin
      import java.io._
      val pw = new PrintWriter(new File(setPath))
      pw.write(luaContentSetPath)
      pw.close

      s"luarocks install --tree $lm ${args.head}" !
    },
      th := {
        val args = spaceDelimited("").parsed
        val bd = baseDirectory.value
        val md = bd + "/models"
        val lm = md + "/lua_modules"
        val setPath = md + "/set_path.lua"

        s"th -l $setPath ${args.head}" !
      }
    )
  }



}
