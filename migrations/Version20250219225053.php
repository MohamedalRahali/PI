<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250219225053 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE blogs (id INT AUTO_INCREMENT NOT NULL, titre VARCHAR(255) NOT NULL, descr VARCHAR(255) NOT NULL, date_crea DATE NOT NULL, date_pub DATE NOT NULL, type_bid INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE type_b (id INT AUTO_INCREMENT NOT NULL, rela_id INT DEFAULT NULL, libelle VARCHAR(255) NOT NULL, INDEX IDX_6E0F8472F8FA969D (rela_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE type_b ADD CONSTRAINT FK_6E0F8472F8FA969D FOREIGN KEY (rela_id) REFERENCES blogs (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE type_b DROP FOREIGN KEY FK_6E0F8472F8FA969D');
        $this->addSql('DROP TABLE blogs');
        $this->addSql('DROP TABLE type_b');
    }
}
