package co.zw.santech.registration.repo;

import co.zw.santech.registration.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    @Query(value = "Select * from verification_token where user_id =:userId", nativeQuery = true)
    Optional<VerificationToken> findTokenByUserId(@Param("userId") Long userId);
}
