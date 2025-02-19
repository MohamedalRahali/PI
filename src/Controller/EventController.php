<?php
namespace App\Controller;
use App\Entity\Evenment;

use App\Repository\EvenmentRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

final class EventController extends AbstractController
{
    #[Route('/event', name: 'app_event')]
    public function index(): Response
    {
        return $this->render('base.html.twig');
    }

    #[Route('/events', name: 'evenment_x', methods: ['GET'])]
    public function listEvents(EvenmentRepository $evenmentRepository): Response
    {
        $evenments = $evenmentRepository->findAll();

        return $this->render('evenment/frontevent.html.twig', [
            'evenments' => $evenments,
        ]);
    }
    #[Route('/events/{id}', name: 'evenment_show2')]
    public function show(Evenment $evenment): Response
    {
        return $this->render('evenment/show2.html.twig', [
            'evenment' => $evenment,
        ]);
    }
}
