package se.vgregion.portal.cs.migration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.vgregion.portal.cs.migration.service.MigrationService;
import se.vgregion.portal.cs.util.AesCtrCryptoUtilImpl;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

/**
 * @author Patrik Bergström
 */
public class PasswordEncryptionMigrator {

    private static final Logger LOGGER = Logger.getLogger(PasswordEncryptionMigrator.class.getName());

    private MigrationService migrationService;

    public static void main(String[] args) throws GeneralSecurityException, NoSuchFieldException, IllegalAccessException {

        LOGGER.info("Starting...");

        if (args.length != 1 || !(args[0].equalsIgnoreCase("ecb2ctr") || args[0].equalsIgnoreCase("ctr2ecb")
                || args[0].equalsIgnoreCase("updatekey") || args[0].equalsIgnoreCase("undoupdatekey"))) {
            System.out.println("Add \"ecb2ctr\", \"ctr2ecb\", \"updateKey\" or \"undoUpdateKey\" as input "
                    + "parameter");
            System.exit(0);
        }
        String cmd = args[0];

        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:migration-jpa-infrastructure.xml",
                "classpath:crypto-util.xml");

        MigrationService service = ctx.getBean(MigrationService.class);

        String path = PasswordEncryptionMigrator.class.getClassLoader().getResource(service.getKeyFilePath())
                .getPath();
        AesCtrCryptoUtilImpl aesCtrCryptoUtil = new AesCtrCryptoUtilImpl();
        aesCtrCryptoUtil.setKeyFile(new File(path));

        service.setAesCtrCryptoUtil(aesCtrCryptoUtil);

        PasswordEncryptionMigrator migrator = new PasswordEncryptionMigrator();
        migrator.setMigrationService(service);

        //Do it
        if (cmd.equalsIgnoreCase("ecb2ctr")) {
            migrator.migrateEcbToCtr();
        } else if (cmd.equalsIgnoreCase("ctr2ecb")) {
            migrator.migrateCtr2Ecb();
        } else if (cmd.equalsIgnoreCase("updatekey")) {
            File newKeyFile = migrator.migrateAndUpdateKey();
            System.out.println("New key file: " + newKeyFile.getAbsolutePath());
        } else if (cmd.equalsIgnoreCase("undoupdatekey")) {
            migrator.undoMigrateAndUpdateKey();
        } else {
            throw new IllegalArgumentException("wrong arguments");
        }
    }

    private void undoMigrateAndUpdateKey() {
        migrationService.undoMigrateAndUpdateKey();
    }

    private File migrateAndUpdateKey() {
        return migrationService.migrateAndUpdateKey();
    }

    private void migrateEcbToCtr() {
        migrationService.migrateEcbToCtr();
    }

    private void migrateCtr2Ecb() {
        migrationService.migrateCtr2Ecb();
    }

    public void setMigrationService(MigrationService credentialService) {
        this.migrationService = credentialService;
    }
}
