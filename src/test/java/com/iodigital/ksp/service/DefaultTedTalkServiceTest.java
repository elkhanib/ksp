package com.iodigital.ksp.service;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.TedTalk;
import com.iodigital.ksp.repository.TedTalkRepository;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultTedTalkService.class, ModelMapper.class, TedTalkRepository.class})
class DefaultTedTalkServiceTest {
    private static final Long ID = 55L;
    private static final String TITLE = "Passionate is the key!";
    private static final String AUTHOR = "Elkhan Ibrahimov";
    private static final LocalDate TALK_DATE = LocalDate.of(2022, 6, 8);
    private static final Long VIEW_COUNT = 1L;
    private static final Long LIKE_COUNT = 0L;
    private static final String LINK = "https://elkhanib.dev";

    private static final TedTalk DEFAULT_TALK = new TedTalk(ID, TITLE, AUTHOR, TALK_DATE, VIEW_COUNT, LIKE_COUNT, LINK);

    @Autowired
    private DefaultTedTalkService tedTalkService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private Validator validator;

    @MockBean
    private TedTalkRepository repository;

    @BeforeEach
    public void init() {
        when(repository.findById(ID)).thenReturn(Optional.of(DEFAULT_TALK));
    }

    @DisplayName("partially updates ted-talk record in db")
    @Test
    void partiallyUpdate() {
        //Arrange
        String newAuthor = "Elon Musk";
        String newTitle = "Not passion, but discipline is the key!";
        LocalDate newTalkDate = LocalDate.of(2022, 6, 22);
        Long newViewCount = 555555L;
        Long newLikeCount = 111111L;

        Map<String, Object> given = Map.of(
                "author", newAuthor,
                "viewCount", newViewCount,
                "title", newTitle,
                "likeCount", newLikeCount,
                "talkDate", newTalkDate
        );
        CreateTedTalkRequest dto = new CreateTedTalkRequest(newTitle, newAuthor, newTalkDate, newViewCount, newLikeCount, LINK);
        TedTalk expected = new TedTalk(ID, newTitle, newAuthor, newTalkDate, newViewCount, newLikeCount, LINK);

        when(validator.validate(dto)).thenReturn(Set.of());
        when(repository.save(expected)).thenReturn(expected);

        //Act
        TedTalk actual = tedTalkService.partiallyUpdate(ID, given);

        //Assert
        assertEquals(expected, actual);
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(expected);
        verify(validator, times(1)).validate(dto);
    }

    @DisplayName("it ignores unknown properties and updates ted-talk record in db ")
    @Test
    void partiallyUpdateWithIgnoringUnknownProperties() {
        //Arrange
        String newAuthor = "Elon Musk";
        String newLink = "https://tesla.com";

        Map<String, Object> given = Map.of(
                "mockField", "mock-data",
                "id", 44444L,
                "author", newAuthor,
                "link", newLink
        );
        TedTalk expected = new TedTalk(ID, TITLE, newAuthor, TALK_DATE, VIEW_COUNT, LIKE_COUNT, newLink);
        CreateTedTalkRequest dto = new CreateTedTalkRequest(TITLE, newAuthor, TALK_DATE, VIEW_COUNT, LIKE_COUNT, newLink);

        when(validator.validate(dto)).thenReturn(Set.of());
        when(repository.save(expected)).thenReturn(expected);

        //Act
        TedTalk actual = tedTalkService.partiallyUpdate(ID, given);

        //Assert
        assertEquals(expected, actual);
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(expected);
        verify(validator, times(1)).validate(dto);
    }

    @DisplayName("it throws exception on updating record with invalid values")
    @Test
    void partiallyUpdateThrowsExceptionWhenConstraintsViolated() {
        //Arrange
        String newAuthor = "";
        String newTitle = "";
        Long newViewCount = -55L;
        Long newLikeCount = -11L;
        String newLink = "";

        Map<String, Object> given = Map.of(
                "author", newAuthor,
                "viewCount", newViewCount,
                "title", newTitle,
                "likeCount", newLikeCount,
                "link", newLink
        );
        CreateTedTalkRequest dto = new CreateTedTalkRequest(newTitle, newAuthor, TALK_DATE, newViewCount, newLikeCount, newLink);
        Set<ConstraintViolation<CreateTedTalkRequest>> violations = Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .validate(dto);
        when(validator.validate(dto)).thenReturn(violations);

        //Act
        assertThrows(ConstraintViolationException.class, () -> tedTalkService.partiallyUpdate(ID, given));

        //Assert
        verify(repository, times(1)).findById(ID);
        verify(validator, times(1)).validate(any());
        verify(repository, never()).save(any());
    }
}