<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Blogs;

final class PopularController extends AbstractController
{
    #[Route('/api/popular-blogs', name: 'api_popular_blogs', methods: ['GET'])]
    public function getPopularBlogs(EntityManagerInterface $entityManager): JsonResponse
    {
        $blogs = $entityManager->getRepository(Blogs::class)->findAll();

        usort($blogs, function($a, $b) {
            return $b->getLikes() <=> $a->getLikes();
        });

        $popularBlogs = array_slice($blogs, 0, 5);

        $data = [];
        foreach ($popularBlogs as $blog) {
            $data[] = [
                'id' => $blog->getId(),
                'titre' => $blog->getTitre(),
                'likes' => $blog->getLikes(),
                'dateCrea' => $blog->getDateCrea()->format('Y-m-d'),
            ];
        }

        return new JsonResponse($data);
    }
}
