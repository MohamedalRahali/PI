<?php

namespace App\Entity;

use App\Repository\BlogsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

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

    #[ORM\Column(type: 'integer')]
    private int $likes = 0;

    /**
     * @var Collection<int, TypeB>
     */
    #[ORM\OneToMany(targetEntity: TypeB::class, mappedBy: 'rela')]
    private Collection $typeBs;

    /**
     * @var Collection<int, Comment>
     */
    #[ORM\OneToMany(targetEntity: Comment::class, mappedBy: 'relat')]
    private Collection $comments;

    public function __construct()
    {
        $this->dateCrea = new \DateTime();
        $this->typeBs = new ArrayCollection();
        $this->comments = new ArrayCollection();
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

    public function getTypeB(): ?TypeB
    {
        return $this->typeB;
    }

    public function setTypeB(?TypeB $typeB): self
    {
        $this->typeB = $typeB;

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

    /**
     * @return Collection<int, Comment>
     */
    public function getComments(): Collection
    {
        return $this->comments;
    }

    public function addComment(Comment $comment): static
    {
        if (!$this->comments->contains($comment)) {
            $this->comments->add($comment);
            $comment->setRelat($this);
        }

        return $this;
    }

    public function removeComment(Comment $comment): static
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getRelat() === $this) {
                $comment->setRelat(null);
            }
        }

        return $this;
    }

    public function getLikes(): int
    {
        return $this->likes;
    }

    public function setLikes(int $likes): self
    {
        $this->likes = $likes;

        return $this;
    }

    public function incrementLikes(): self
    {
        $this->likes++;

        return $this;
    }

}
