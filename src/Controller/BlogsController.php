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
use App\Form\BlogSearchType;
use App\Entity\Comment;
use App\Form\CommentType;
use App\Entity\Like;
use App\Service\PdfService;

final class BlogsController extends AbstractController
{
    private $blogsRepo;
    private $entityManager;
    private $inappropriateWords = ['mem', 'ttt', 'sss'];

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
    public function BlogsList(Request $request, BlogsRepository $blogsRepository): Response
    {
        $form = $this->createForm(BlogSearchType::class);
        $form->handleRequest($request);

        $searchTerm = $request->query->get('search', '');

        $queryBuilder = $blogsRepository->createQueryBuilder('b');

        if ($form->isSubmitted() && $form->isValid()) {
            $data = $form->getData();

            if (!empty($data['titre'])) {
                $queryBuilder->andWhere('b.titre LIKE :titre')
                    ->setParameter('titre', '%' . $data['titre'] . '%');
            }

            if (!empty($data['descr'])) {
                $queryBuilder->andWhere('b.descr LIKE :descr')
                    ->setParameter('descr', '%' . $data['descr'] . '%');
            }

            if (!empty($data['dateCrea'])) {
                $queryBuilder->andWhere('b.dateCrea = :dateCrea')
                    ->setParameter('dateCrea', $data['dateCrea']);
            }

            if (!empty($data['typeBs'])) {
                $queryBuilder->andWhere('b.typeBs IN (:typeBs)')
                    ->setParameter('typeBs', $data['typeBs']);
            }
        } elseif ($searchTerm) {
            $queryBuilder->where('b.titre LIKE :search OR b.descr LIKE :search')
                ->setParameter('search', '%' . $searchTerm . '%');
        }

        $blogs = $queryBuilder->getQuery()->getResult();

        return $this->render('blogs/list.html.twig', [
            'form' => $form->createView(),
            'blogs' => $blogs,
            'searchTerm' => $searchTerm,
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
    #[Route('/showBlogs/{id}', name: 'app_showBlog', methods: ['GET', "POST"])]
    public function show(Blogs $blog, Request $request, EntityManagerInterface $entityManager): Response
    {
        $comment = new Comment();
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $comment->setRelat($blog);
            $entityManager->persist($comment);
            $entityManager->flush();

            if ($comment->containsInappropriateWords($this->inappropriateWords)) {
                $comment->setIsReported(true);
                $entityManager->flush();
            }

            return $this->redirectToRoute('app_showBlog', ['id' => $blog->getId()]);
        }

        return $this->render('blogs/show.html.twig', [
            'blog' => $blog,
            'form' => $form->createView(),
            'inappropriateWords' => $this->inappropriateWords,
        ]);
    }

    #[Route('/blog/{id}/like', name: 'app_blogLike', methods: ["POST"])]
    public function likeBlog(Blogs $blog, EntityManagerInterface $entityManager): Response
    {
        $blog->incrementLikes();
        $entityManager->flush();

        return $this->redirectToRoute('app_showBlog', ['id' => $blog->getId()]);
    }
    
    #[Route('/comment/{id}/delete', name: 'app_deleteComment', methods: ['DELETE', 'POST'])]
    public function deleteComment(Comment $comment, EntityManagerInterface $entityManager): Response
    {
        $entityManager->remove($comment);
        $entityManager->flush();

        return $this->redirectToRoute('app_showBlog', ['id' => $comment->getRelat()->getId()]);
    }

    #[Route('/comment/{id}/report', name: 'app_reportComment', methods: ["POST"])]
    public function reportComment(Comment $comment, EntityManagerInterface $entityManager): Response
    {
        $comment->setIsReported(true);
        $entityManager->flush();

        return $this->redirectToRoute('app_showBlog', ['id' => $comment->getRelat()->getId()]);
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


    #[Route('/blogs/search', name: 'app_search')]
    public function search(Request $request, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(BlogSearchType::class);
        $form->handleRequest($request);

        $queryBuilder = $entityManager->getRepository(Blogs::class)->createQueryBuilder('b');

        if ($form->isSubmitted() && $form->isValid()) {
            $data = $form->getData();

            if (!empty($data['titre'])) {
                $queryBuilder->andWhere('b.titre LIKE :titre')
                    ->setParameter('titre', '%' . $data['titre'] . '%');
            }

            if (!empty($data['descr'])) {
                $queryBuilder->andWhere('b.descr LIKE :descr')
                    ->setParameter('descr', '%' . $data['descr'] . '%');
            }

            if (!empty($data['dateCrea'])) {
                $queryBuilder->andWhere('b.dateCrea = :dateCrea')
                    ->setParameter('dateCrea', $data['dateCrea']);
            }

            if (!empty($data['typeBs'])) {
                $queryBuilder->andWhere('b.typeBs IN (:typeBs)')
                    ->setParameter('typeBs', $data['typeBs']);
            }
        }

        $results = $queryBuilder->getQuery()->getResult();

        return $this->render('blogs/search.html.twig', [
            'form' => $form->createView(),
            'results' => $results,
        ]);
    }

    #[Route('/pdf', name: 'app_pdf')]
    public function downloadPdf(BlogsRepository $blogsRepository, PdfService $pdfService): Response
    {
        $blogs = $blogsRepository->findAll();

        $html = $this->renderView('blogs/listp.html.twig', [
            'blogs' => $blogs,
        ]);

        $pdfContent = $pdfService->generatePdf($html);

        return new Response($pdfContent, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'inline; filename="blogs.pdf"',
        ]);
    }
}