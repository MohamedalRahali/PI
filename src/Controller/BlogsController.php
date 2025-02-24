<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use App\Repository\BlogsRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Blogs;
use Symfony\Component\HttpFoundation\Request;
use App\Form\BlogsType;

final class BlogsController extends AbstractController
{
    private $blogsRepo;
    private $entityManager;

    public function __construct(BlogsRepository $BlogsRepositoryParam,EntityManagerInterface $entityManagerParam)
    {
        $this->BlogsRepo = $BlogsRepositoryParam; 
        $this->entityManager=$entityManagerParam;
    }

    
    #[Route('/blogs', name: 'app_blogs')]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $blogs = $entityManager->getRepository(Blogs::class)->findAll();

        return $this->render('blogs/index.html.twig', [
            'blogs' => $blogs,
        ]);
    }


    
    #[Route('/blogsList', name: 'app_blogsList', methods:['GET'])]
    public function BlogsList(): Response
    {
        $blogs = $this->BlogsRepo->findAllBlogs();
        return $this->render('blogs/list.html.twig', [
            'blogs' => $blogs,
        ]);
    }
    #[Route('/addBlogs', name: 'addBlogs', methods: ['GET', 'POST'])]
    public function addBlog(Request $request, EntityManagerInterface $entityManager): Response
    {
        $blog = new Blogs();
        $form = $this->createForm(BlogsType::class, $blog);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($blog);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_blogsList');
        }
    
        $blogs = $this->BlogsRepo->findAllBlogs();

        return $this->render('blogs/add.html.twig', [
            'blogs' => $blogs,
            'form' => $form->createView(),
        ]);
    }
    #[Route('/showBlogs/{id}', name: 'app_showBlog', methods: ['GET'])]
    public function show(Blogs $blogs): Response
    {
        return $this->render('blogs/show.html.twig', [
            'blog' => $blogs,
        ]);
    }
    #[Route('/edit/{id}', name: 'app_editBlog', methods: ['GET', 'POST'])]
    public function edit(Request $request, int $id, BlogsRepository $blogsRepository, EntityManagerInterface $entityManager): Response
    {
        $blogs = $blogsRepository->find($id);

        if (!$blogs) {
            throw $this->createNotFoundException('Blog not found!');
        }

        $form = $this->createForm(BlogsType::class, $blogs);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_blogsList');
        }

        return $this->render('blogs/edit.html.twig', [
            'form' => $form->createView(),
            'blog' => $blogs, 
        ]);
    }


    #[Route('/delete/{id}', name: 'app_deleteBlog', methods: ['GET','DELETE'])]
    public function delete(Blogs $blog):Response{
        if($blog){
        $this->entityManager->remove($blog);
        $this->entityManager->flush();
        }
        return $this->redirectToRoute('app_blogsList');
    }
    
}