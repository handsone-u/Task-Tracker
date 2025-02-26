package io.handsone.tasktracker.util;

import io.handsone.tasktracker.configuration.JasyptConfiguration;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestPropertySource(properties = {
    "jasypt.encryptor.password=testpassword",
    "jasypt.encryptor.algorithm=PBEWithMD5AndDES"
})
@ContextConfiguration(classes = JasyptConfiguration.class)
class JasyptConfigurationTest {

  @Autowired
  StringEncryptor jasyptStringEncryptor;

  @Test
  public void testEncrypt() {
    String plainPassword = "password";
    String encryptedPassword = jasyptStringEncryptor.encrypt(plainPassword);
    System.out.println("encryptedPassword = " + encryptedPassword);

    String decryptedPassword = jasyptStringEncryptor.decrypt(encryptedPassword);
    System.out.println("decryptedPassword = " + decryptedPassword);
  }

  @Test
  public void testDecrypt() {
    String encryptedPassword = "Hpk75MCq87tU2qZtdq5z1HhzqpCubDcZ";
    String decryptedPassword = jasyptStringEncryptor.decrypt(encryptedPassword);
    System.out.println("decryptedPassword = " + decryptedPassword);
  }
}
