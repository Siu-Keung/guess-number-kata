package tw.core;


import com.google.inject.matcher.Matchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.creation.bytebuddy.MockAccess;
import tw.core.exception.AnswerFormatIncorrectException;
import tw.core.model.Record;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jxzhong on 2017/9/23.
 */
public class AnswerTest {
    private Answer actualAnswer;

    @BeforeEach
    public void setUp() {
        actualAnswer = Answer.createAnswer("1 2 3 4");
    }

    @Test
    public void should_get_1234_when_call_createAnswer(){
        Answer answer = Answer.createAnswer("1 2 3 4");

        assertThat(answer.toString(), is("1 2 3 4"));
    }

    @Test
    public void should_not_throw_exception_when_format_correct() {
        Answer answer = Answer.createAnswer("1 2 3 4");

        try {
            answer.validate();
        } catch (AnswerFormatIncorrectException e) {
            Assertions.fail("不应当抛异常");
        }
    }

    @Test
    public void should_throw_exception_when_contain_char(){
        Answer answer = Answer.createAnswer("1 2, 3 4");
        try {
            answer.validate();
        } catch (Exception e) {
            return;
        }
        Assertions.fail("输入含有字符，应当抛异常");
    }

    @Test
    public void should_throw_exception_when_num_greater_than_9(){
        Answer answer = Answer.createAnswer("1 2 13 4");
        try {
            answer.validate();
        } catch (AnswerFormatIncorrectException e) {
            return;
        }
        Assertions.fail("输入数字大于9，应当抛异常");
    }

    @Test
    public void should_get_true_value_when_input_correct(){
        Answer mockAnswer = mock(Answer.class);
        when(mockAnswer.getIndexOfNum("1")).thenReturn(0);
        when(mockAnswer.getIndexOfNum("2")).thenReturn(1);
        when(mockAnswer.getIndexOfNum("3")).thenReturn(2);
        when(mockAnswer.getIndexOfNum("4")).thenReturn(3);

        Record record = actualAnswer.check(mockAnswer);

        assertThat(record.getValue(), is("4A0B"));
    }


}