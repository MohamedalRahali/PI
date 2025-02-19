<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use App\Repository\TypeBRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\TypeB;
use Symfony\Component\HttpFoundation\Request;
use App\Form\TypeBType;

final class TypeBController extends AbstractController
{
    private $typeBsRepo;
    private $entityManager;

    public function __construct(TypeBRepository $TypeBRepositoryParam,EntityManagerInterface $entityManagerParam)
    {
        $this->TypeBRepo = $TypeBRepositoryParam; 
        $this->entityManager=$entityManagerParam;
    }

    #[Route('/Types', name: 'app_types')]
    public function index(): Response
    {
        return $this->render('Type_b/index.html.twig', [
            'controller_name' => 'TypeBController',
        ]);
    }
    #[Route('/typesList', name: 'app_typesList', methods:['GET'])]
    public function typeBList(): Response
    {
        $typeBs = $this->TypeBRepo->findAllTypeB();
        return $this->render('Type_b/list.html.twig', [
            'typeBs' => $typeBs,
        ]);
    }
    #[Route('/addTypes', name: 'addTypes', methods: ['GET', 'POST'])]
    public function addType(Request $request, EntityManagerInterface $entityManager): Response
    {
        $typeB = new TypeB();
        $form = $this->createForm(TypeBType::class, $typeB);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($typeB);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_typesList');
        }
    
        $typeB = $this->TypeBRepo->findAllTypeB();

        return $this->render('Type_b/add.html.twig', [
            'typeB' => $typeB,
            'form' => $form->createView(),
        ]);
    }
    #[Route('/showTypes/{id}', name: 'app_showTypes', methods: ['GET'])]
    public function show(TypeB $typeB): Response
    {
        return $this->render('Type_b/show.html.twig', [
            'typeB' => $typeB,
        ]);
    }
    #[Route('/edit/{id}', name: 'app_editType', methods: ['GET', 'POST'])]
    public function edit(Request $request, TypeB $typeB, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TypeBType::class, $typeB);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_typesList');
        }

        return $this->render('Type_b/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    #[Route('/delete/{id}', name: 'app_deleteType', methods: ['GET','DELETE'])]
    public function delete(TypeB $typeB):Response{
        if($typeB){
        $this->entityManager->remove($typeB);
        $this->entityManager->flush();
        }
        return $this->redirectToRoute('app_typesList');
    }
    
}