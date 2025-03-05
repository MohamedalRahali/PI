<?php

namespace App\Entity;

use App\Repository\CommentRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommentRepository::class)]
class Comment
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::TEXT)]
    private ?string $content = null;

    #[ORM\ManyToOne(targetEntity: Blogs::class, inversedBy: 'comments')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Blogs $relat = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $createdAt = null;

    #[ORM\Column(type: 'boolean')]
    private bool $isReported = false;

    public function __construct()
    {
        $this->createdAt = new \DateTime();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getContent(): ?string
    {
        return $this->content;
    }

    public function setContent(string $content): static
    {
        $this->content = $content;

        return $this;
    }

    

    public function getRelat(): ?Blogs
    {
        return $this->relat;
    }

    public function setRelat(?Blogs $relat): static
    {
        $this->relat = $relat;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeInterface $createdAt): static
    {
        $this->createdAt = $createdAt;

        return $this;
    }

    public function isIsReported(): bool
    {
        return $this->isReported;
    }

    public function setIsReported(bool $isReported): self
    {
        $this->isReported = $isReported;

        return $this;
    }

    public function containsInappropriateWords(array $inappropriateWords): bool
    {
        foreach ($inappropriateWords as $word) {
            if (stripos($this->content, $word) !== false) {
                return true;
            }
        }
        return false;
    }

    public function getInappropriateWords(array $inappropriateWords): array
    {
        $foundWords = [];
        foreach ($inappropriateWords as $word) {
            if (stripos($this->content, $word) !== false) {
                $foundWords[] = $word;
            }
        }
        return $foundWords;
    }
}
