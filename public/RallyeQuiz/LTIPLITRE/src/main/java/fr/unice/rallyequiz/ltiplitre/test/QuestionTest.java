package fr.unice.rallyequiz.ltiplitre.test;

import android.test.ActivityInstrumentationTestCase2;

import fr.unice.rallyequiz.ltiplitre.Question;

import static org.mockito.Mockito.mock;

/**
 * Created by yezide on 14/05/2014.
 */
public class QuestionTest extends ActivityInstrumentationTestCase2<Question> {
    public QuestionTest(Class<Question> activityClass) {
        super(activityClass);
        }
    public void test_recupererLesQuestions() throws Exception {
        Question quiz = new Question();
        Question mockQuiz = mock(Question.class);
        String quizString = quiz.recupererLesQuestions();
        String mockString = mockQuiz.recupererLesQuestions();
        assertNotNull(mockString);
        assertNotNull(quizString);
        assertEquals(quizString,mockQuiz);
        }
}
