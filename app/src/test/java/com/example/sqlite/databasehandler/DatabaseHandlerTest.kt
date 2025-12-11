
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.example.sqlite.database.DatabaseHandler
import com.example.sqlite.entity.Cadastro
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DatabaseHandlerTest {

    private lateinit var databaseHandler: DatabaseHandler

    @Before
    fun setUp() {
        databaseHandler = DatabaseHandler(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        databaseHandler.close()
    }

    @Test
    fun testInsertAndSearch() {
        val cadastro = Cadastro(1, "Thiago", "123456789")
        databaseHandler.insert(cadastro)

        val result = databaseHandler.search(1)

        assertNotNull(result)
        assertEquals("Thiago", result?.name)
        assertEquals("123456789", result?.telefone)
    }

    @Test
    fun testUpdate() {
        val cadastro = Cadastro(1, "Thiago", "123456789")
        databaseHandler.insert(cadastro)

        val updatedCadastro = Cadastro(1, "Thiago Monteiro", "987654321")
        databaseHandler.update(updatedCadastro)

        val result = databaseHandler.search(1)

        assertNotNull(result)
        assertEquals("Thiago Monteiro", result?.name)
        assertEquals("987654321", result?.telefone)
    }

    @Test
    fun testDelete() {
        val cadastro = Cadastro(1, "Thiago", "123456789")
        databaseHandler.insert(cadastro)

        databaseHandler.delete(1)

        val result = databaseHandler.search(1)

        assertNull(result)
    }

    @Test
    fun testList() {
        val cadastro1 = Cadastro(1, "Thiago", "123456789")
        val cadastro2 = Cadastro(2, "Monteiro", "987654321")
        databaseHandler.insert(cadastro1)
        databaseHandler.insert(cadastro2)

        val cursor = databaseHandler.list()

        assertEquals(2, cursor.count)
    }
}