package lobbi44.kt.command

//import com.nhaarman.mockito_kotlin.*
//import org.mockito.Mockito.mock
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import lobbi44.kt.command.annotations.Command
import org.bukkit.Server
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.SimplePluginManager
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.logging.Logger

@Ignore
internal class CommandFrameworkTest {

    @Test
    fun registerCommandsRecognizesCorrectMethods() {
        val logger = mock(Logger::class.java)
        val plugin = mock(Plugin::class.java)
        val server = mock(Server::class.java)
        val commandMap = mock(SimpleCommandMap::class.java)
        val pluginManager = SimplePluginManager(server, commandMap)

        `when`(plugin.server).thenReturn(server)
        `when`(server.pluginManager).thenReturn(pluginManager)

        val framework = CommandFramework(plugin, logger)
        val res = framework.registerCommands(TestCommands1())
        assertEquals(1, res)
    }

    @Ignore
    @Test
    fun onCommandCallsDelegatedMethods() {
        val logger = mock(Logger::class.java)
        val plugin = mock(Plugin::class.java)
        val server = mock(Server::class.java)
        val commandMap = mock(SimpleCommandMap::class.java)
        val pluginManager = SimplePluginManager(server, commandMap)

        `when`(plugin.server).thenReturn(server)
        `when`(server.pluginManager).thenReturn(pluginManager)

        val framework = CommandFramework(plugin, logger)
        val exeCommand: TestCommands1 = spy()
        framework.registerCommands(exeCommand)

        framework.onCommand(mock(), mock(), "Command1", null)
        verify(exeCommand).Command1(argThat { label == "Command1" })

    }


    @Ignore
    @Test
    fun testOnCommandArgumentOmitting() {
        val logger = mock(Logger::class.java)
        val plugin = mock(Plugin::class.java)
        val server = mock(Server::class.java)
        val commandMap = mock(SimpleCommandMap::class.java)
        val pluginManager = SimplePluginManager(server, commandMap)

        `when`(plugin.server).thenReturn(server)
        `when`(server.pluginManager).thenReturn(pluginManager)

        val framework = CommandFramework(plugin, logger)
        val exeCommand: TestCommands2 = spy()
        framework.registerCommands(exeCommand)

        framework.onCommand(mock(), mock(), "Command", arrayOf("sub1", "sub2", "arg1", "arg2"))
        verify(exeCommand).Command1(argThat { args.size == 2 })

    }

    open class TestCommands1 {
        @Command(name = "Command1")
        fun Command1(event: CommandEvent): Boolean {
            return true
        }
    }

    open class TestCommands2 {
        @Command(name = "Command.sub1.sub2")
        fun Command1(event: CommandEvent): Boolean {
            return true
        }
    }

}
