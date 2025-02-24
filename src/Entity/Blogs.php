<?php

namespace App\Entity;

use App\Repository\BlogsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BlogsRepository::class)]
class Blogs
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $titre = null;

    #[ORM\Column(length: 255)]
    private ?string $descr = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateCrea = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $datePub = null;

    /**
     * @var Collection<int, TypeB>
     */
    #[ORM\OneToMany(targetEntity: TypeB::class, mappedBy: 'rela')]
    private Collection $typeBs;

    public function __construct()
    {
        $this->typeBs = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
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

    public function getDescr(): ?string
    {
        return $this->descr;
    }

    public function setDescr(string $descr): static
    {
        $this->descr = $descr;

        return $this;
    }

    public function getDateCrea(): ?\DateTimeInterface
    {
        return $this->dateCrea;
    }

    public function setDateCrea(\DateTimeInterface $dateCrea): static
    {
        $this->dateCrea = $dateCrea;

        return $this;
    }

    public function getDatePub(): ?\DateTimeInterface
    {
        return $this->datePub;
    }

    public function setDatePub(\DateTimeInterface $datePub): static
    {
        $this->datePub = $datePub;

        return $this;
    }

    /**
     * @return Collection<int, TypeB>
     */
    public function getTypeBs(): Collection
    {
        return $this->typeBs;
    }

    public function addTypeB(TypeB $typeB): static
    {
        if (!$this->typeBs->contains($typeB)) {
            $this->typeBs->add($typeB);
            $typeB->setRela($this);
        }

        return $this;
    }

    public function removeTypeB(TypeB $typeB): static
    {
        if ($this->typeBs->removeElement($typeB)) {

            if ($typeB->getRela() === $this) {
                $typeB->setRela(null);
            }
        }

        return $this;
    }
}
