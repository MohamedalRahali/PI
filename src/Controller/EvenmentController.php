<?php

namespace App\Controller;

use App\Entity\Evenment;
use App\Form\EvenmentType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Repository\EvenmentRepository;

class EvenmentController extends AbstractController
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/evenment', name: 'evenment_index')]
    public function index(EvenmentRepository $evenmentRepository): Response
    {
        return $this->render('evenment/index.html.twig', [
            'evenments' => $evenmentRepository->findAll(),
        ]);
    }
    

    #[Route('/evenment/new', name: 'evenment_new')]
    public function new(Request $request): Response
    {
        $evenment = new Evenment();
        $form = $this->createForm(EvenmentType::class, $evenment);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Persist the Evenment entity
            $this->entityManager->persist($evenment);
            $this->entityManager->flush();

            // Redirect to the index page after successful creation
            return $this->redirectToRoute('evenment_index');
        }

        return $this->render('evenment/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/evenment/{id}', name: 'evenment_show')]
    public function show(Evenment $evenment): Response
    {
        return $this->render('evenment/show.html.twig', [
            'evenment' => $evenment,
        ]);
    } 

    #[Route('/evenment/{id}/edit', name: 'evenment_edit')]
    public function edit(Request $request, Evenment $evenment): Response
    {
        $form = $this->createForm(EvenmentType::class, $evenment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Save changes to the Evenment entity
            $this->entityManager->flush();

            // Redirect to the index page after successful update
            return $this->redirectToRoute('evenment_index');
        }

        return $this->render('evenment/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/evenment/{id}/delete', name: 'evenment_delete')]
    public function delete(Request $request, Evenment $evenment): Response
    {
        // Check for CSRF token validity
        if ($this->isCsrfTokenValid('delete' . $evenment->getId(), $request->request->get('_token'))) {
            // Remove the Evenment entity
            $this->entityManager->remove($evenment);
            $this->entityManager->flush();
        }

        // Redirect to the index page after deletion
        return $this->redirectToRoute('evenment_index');
    }



}