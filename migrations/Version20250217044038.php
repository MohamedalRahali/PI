<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250217044038 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE evenment ADD start_time TIME NOT NULL, ADD end_time TIME NOT NULL, ADD available_places INT NOT NULL');
        $this->addSql('ALTER TABLE t CHANGE evenment_id evenment_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE t ADD CONSTRAINT FK_856A5AA88BC15922 FOREIGN KEY (evenment_id) REFERENCES evenment (id)');
        $this->addSql('CREATE INDEX IDX_856A5AA88BC15922 ON t (evenment_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE evenment DROP start_time, DROP end_time, DROP available_places');
        $this->addSql('ALTER TABLE t DROP FOREIGN KEY FK_856A5AA88BC15922');
        $this->addSql('DROP INDEX IDX_856A5AA88BC15922 ON t');
        $this->addSql('ALTER TABLE t CHANGE evenment_id evenment_id INT NOT NULL');
    }
}
