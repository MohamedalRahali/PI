<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250209213841 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE eventcate (id INT AUTO_INCREMENT NOT NULL, relations_id INT DEFAULT NULL, name VARCHAR(255) NOT NULL, desc_event VARCHAR(255) NOT NULL, INDEX IDX_6BEDD3EA1BFA63C8 (relations_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE eventcate ADD CONSTRAINT FK_6BEDD3EA1BFA63C8 FOREIGN KEY (relations_id) REFERENCES event (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE eventcate DROP FOREIGN KEY FK_6BEDD3EA1BFA63C8');
        $this->addSql('DROP TABLE eventcate');
    }
}
