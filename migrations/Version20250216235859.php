<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250216235859 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE t_evenment DROP FOREIGN KEY FK_F30D0A215C19F4F5');
        $this->addSql('ALTER TABLE t_evenment DROP FOREIGN KEY FK_F30D0A218BC15922');
        $this->addSql('DROP TABLE t_evenment');
        $this->addSql('ALTER TABLE t ADD evenment_id INT NOT NULL');
        $this->addSql('ALTER TABLE t ADD CONSTRAINT FK_856A5AA88BC15922 FOREIGN KEY (evenment_id) REFERENCES evenment (id)');
        $this->addSql('CREATE INDEX IDX_856A5AA88BC15922 ON t (evenment_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE t_evenment (t_id INT NOT NULL, evenment_id INT NOT NULL, INDEX IDX_F30D0A215C19F4F5 (t_id), INDEX IDX_F30D0A218BC15922 (evenment_id), PRIMARY KEY(t_id, evenment_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE t_evenment ADD CONSTRAINT FK_F30D0A215C19F4F5 FOREIGN KEY (t_id) REFERENCES t (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE t_evenment ADD CONSTRAINT FK_F30D0A218BC15922 FOREIGN KEY (evenment_id) REFERENCES evenment (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE t DROP FOREIGN KEY FK_856A5AA88BC15922');
        $this->addSql('DROP INDEX IDX_856A5AA88BC15922 ON t');
        $this->addSql('ALTER TABLE t DROP evenment_id');
    }
}
