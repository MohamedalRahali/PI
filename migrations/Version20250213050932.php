<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250213050932 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE ticket DROP FOREIGN KEY FK_97A0ADA38BC15922');
        $this->addSql('DROP TABLE ticket');
        $this->addSql('ALTER TABLE evenment DROP ticket_quantity');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE ticket (id INT AUTO_INCREMENT NOT NULL, evenment_id INT NOT NULL, quantity INT NOT NULL, INDEX IDX_97A0ADA38BC15922 (evenment_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE ticket ADD CONSTRAINT FK_97A0ADA38BC15922 FOREIGN KEY (evenment_id) REFERENCES evenment (id)');
        $this->addSql('ALTER TABLE evenment ADD ticket_quantity INT NOT NULL');
    }
}
