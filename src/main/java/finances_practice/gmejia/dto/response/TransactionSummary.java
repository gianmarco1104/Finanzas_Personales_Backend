package finances_practice.gmejia.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Se usa interface porque esto devuelve los datos de una funcion de PostgreSQL en Supabase, Spring hace todo el trabajo
public interface TransactionSummary {
    Long getId();
    BigDecimal getAmount();
    String getDescription();
    LocalDateTime getDate();
    String getCategory_name();
    String getType_name();
    String getPayment_method();
    Boolean getIs_recurring();
}
