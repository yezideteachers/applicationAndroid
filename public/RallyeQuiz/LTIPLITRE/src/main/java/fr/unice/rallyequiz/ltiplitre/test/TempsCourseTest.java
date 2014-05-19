package fr.unice.rallyequiz.ltiplitre.test;

import android.test.ActivityInstrumentationTestCase2;

import fr.unice.rallyequiz.ltiplitre.TempsCourse;

import static org.mockito.Mockito.mock;

/**
 * Created by yezide on 14/05/2014.
 */
public class TempsCourseTest extends ActivityInstrumentationTestCase2<TempsCourse> {
    public TempsCourseTest(Class<TempsCourse> activityClass) {
        super(activityClass);
    }

    public void test_formatTime() throws Exception {
        long time = 122121212; //en nano-seconde
        TempsCourse temps = new TempsCourse();
        TempsCourse mockTemps = mock(TempsCourse.class);
        String tempsString = temps.formatTime(time);
        String mockString = mockTemps.formatTime(time);
        assertNotNull(mockString);
        assertNotNull(tempsString);
        assertEquals(tempsString,mockString);
    }
}
