package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class SpendRecord(
    val cash: Double,
)
{
    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDate.now()
}
