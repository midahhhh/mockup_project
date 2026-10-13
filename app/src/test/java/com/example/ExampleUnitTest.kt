package com.example

import com.example.data.PoskestrenRepository
import com.example.data.TriageLevel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSawCalculationDaruratMerah() {
        val result = PoskestrenRepository.calculateSawScore(
            temp = 39.8,
            painScore = 9,
            comorbidVal = 1.0,
            durationVal = 0.95,
            vitalVal = 1.0
        )
        assertTrue("Total Vi score should be high for emergency", result.totalVi >= 0.75)
        assertEquals(TriageLevel.DARURAT_MERAH, result.level)
    }

    @Test
    fun testSawCalculationRinganHijau() {
        val result = PoskestrenRepository.calculateSawScore(
            temp = 36.6,
            painScore = 2,
            comorbidVal = 0.0,
            durationVal = 0.2,
            vitalVal = 0.1
        )
        assertTrue("Total Vi score should be low for mild case", result.totalVi < 0.45)
        assertEquals(TriageLevel.RINGAN_HIJAU, result.level)
    }
}
