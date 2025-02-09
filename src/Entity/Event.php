<?php

namespace App\Entity;

use App\Repository\EventRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: EventRepository::class)]
class Event
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $title = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(length: 255)]
    private ?string $titre = null;

    #[ORM\Column(length: 255)]
    private ?string $description = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_event = null;

    #[ORM\Column(length: 255)]
    private ?string $lieux = null;

    /**
     * @var Collection<int, Eventcate>
     */
    #[ORM\OneToMany(targetEntity: Eventcate::class, mappedBy: 'relations')]
    private Collection $eventcates;

    public function __construct()
    {
        $this->eventcates = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): static
    {
        $this->date = $date;

        return $this;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): static
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getDateEvent(): ?\DateTimeInterface
    {
        return $this->date_event;
    }

    public function setDateEvent(\DateTimeInterface $date_event): static
    {
        $this->date_event = $date_event;

        return $this;
    }

    public function getLieux(): ?string
    {
        return $this->lieux;
    }

    public function setLieux(string $lieux): static
    {
        $this->lieux = $lieux;

        return $this;
    }

    /**
     * @return Collection<int, Eventcate>
     */
    public function getEventcates(): Collection
    {
        return $this->eventcates;
    }

    public function addEventcate(Eventcate $eventcate): static
    {
        if (!$this->eventcates->contains($eventcate)) {
            $this->eventcates->add($eventcate);
            $eventcate->setRelations($this);
        }

        return $this;
    }

    public function removeEventcate(Eventcate $eventcate): static
    {
        if ($this->eventcates->removeElement($eventcate)) {
            // set the owning side to null (unless already changed)
            if ($eventcate->getRelations() === $this) {
                $eventcate->setRelations(null);
            }
        }

        return $this;
    }
}
