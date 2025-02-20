<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250219224910 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE blogs ADD descr VARCHAR(255) NOT NULL, ADD date_crea DATE NOT NULL, ADD date_pub DATE NOT NULL, ADD type_bid INT NOT NULL, CHANGE title titre VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE type_b CHANGE rela_id rela_id INT DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE blogs ADD title VARCHAR(255) NOT NULL, DROP titre, DROP descr, DROP date_crea, DROP date_pub, DROP type_bid');
        $this->addSql('ALTER TABLE type_b CHANGE rela_id rela_id INT NOT NULL');
    }
}
