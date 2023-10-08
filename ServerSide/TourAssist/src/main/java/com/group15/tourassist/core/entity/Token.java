package com.group15.tourassist.core.entity;

import com.group15.tourassist.core.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author snehitroda
 * @implNote Token entity is used to store the auth tokens associated with a user
 * */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

  @Id
  @GeneratedValue
  public Integer tokenId;

  @Column(unique = true)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean isRevoked;

  public boolean isExpired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "app_user_id")
  public AppUser appUser;
}
