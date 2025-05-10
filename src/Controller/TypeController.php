<?php

namespace App\Controller;

use App\Entity\T;
use App\Form\T1Type;
use App\Repository\TRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/type')]
final class TypeController extends AbstractController{
    #[Route(name: 'app_type_index', methods: ['GET'])]
    public function index(TRepository $tRepository): Response
    {
        return $this->render('type/index.html.twig', [
            'ts' => $tRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_type_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $t = new T();
        $form = $this->createForm(T1Type::class, $t);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($t);
            $entityManager->flush();

            return $this->redirectToRoute('app_type_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('type/new.html.twig', [
            't' => $t,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_type_show', methods: ['GET'])]
    public function show(T $t): Response
    {
        return $this->render('type/show.html.twig', [
            't' => $t,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_type_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, T $t, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(T1Type::class, $t);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_type_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('type/edit.html.twig', [
            't' => $t,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_type_delete', methods: ['POST'])]
    public function delete(Request $request, T $t, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$t->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($t);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_type_index', [], Response::HTTP_SEE_OTHER);
    }
}
