package team.projectpulse.instructor;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.mockito.Mockito.*;

class InstructorSpecsTest {

    @Test
    @SuppressWarnings("unchecked")
    void firstNameSearchMustNotDependOnSystemLocale() {
        Locale originalLocale = Locale.getDefault();

        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));

            Root<Instructor> root = mock(Root.class);
            CriteriaBuilder builder = mock(CriteriaBuilder.class);
            Path<String> namePath = mock(Path.class);
            Expression<String> lowerName = mock(Expression.class);

            when(root.<String>get("firstName")).thenReturn(namePath);
            when(builder.lower(namePath)).thenReturn(lowerName);

            InstructorSpecs.hasFirstName("INSTRUCTOR")
                    .toPredicate(root, null, builder);

            verify(builder).equal(lowerName, "instructor");
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}