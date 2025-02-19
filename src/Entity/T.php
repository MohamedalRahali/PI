<?php

namespace App\Entity;

use App\Repository\TRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
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
    
    #[ORM\ManyToMany(targetEntity: Evenment::class, mappedBy: 'types',cascade: ['remove'])]    
    private Collection $evenments;

    public function __construct()
    {
        $this->evenments = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;
        return $this;
    }

    public function getDescEvent(): ?string
    {
        return $this->desc_event;
    }

    public function setDescEvent(string $desc_event): self
    {
        $this->desc_event = $desc_event;
        return $this;
    }

    /**
     * @return Collection<int, Evenment>
     */
    public function getEvenments(): Collection
    {
        return $this->evenments;
    }

    public function addEvenment(Evenment $evenment): self
    {
        if (!$this->evenments->contains($evenment)) {
            $this->evenments->add($evenment);
            // Keep the bidirectional relationship in sync
            $evenment->addType($this);
        }

        return $this;
    }

    public function removeEvenment(Evenment $evenment): self
    {
        if ($this->evenments->removeElement($evenment)) {
            // Keep the bidirectional relationship in sync
            $evenment->removeType($this);
        }

        return $this;
    }
}
