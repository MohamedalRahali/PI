<?php

namespace App\Entity;

use App\Repository\TRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: TRepository::class)]
class T
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $name = null;

    #[ORM\Column(length: 255)]
    private ?string $desc_event = null;

    #[ORM\ManyToOne(inversedBy: 'Evenment')]
    private ?Evenment $evenment = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): static
    {
        $this->name = $name;

        return $this;
    }

    public function getDescEvent(): ?string
    {
        return $this->desc_event;
    }

    public function setDescEvent(string $desc_event): static
    {
        $this->desc_event = $desc_event;

        return $this;
    }

    public function getEvenment(): ?Evenment
    {
        return $this->evenment;
    }

    public function setEvenment(?Evenment $evenment): static
    {
        $this->evenment = $evenment;

        return $this;
    }
}
