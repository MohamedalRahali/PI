<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\TypeBRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\TypeB;
use Symfony\Component\HttpFoundation\Request;
use App\Form\TypeBType;
use App\Form\TypeBSearchType;

class TypeBController extends AbstractController
{
    private $typeBRepository;
    private $entityManager;

    public function __construct(TypeBRepository $typeBRepository, EntityManagerInterface $entityManager)
    {
        $this->typeBRepository = $typeBRepository;
        $this->entityManager = $entityManager;
    }

    #[Route('/Types', name: 'app_types')]
    public function index(): Response
    {
        return $this->render('Type_b/index.html.twig', [
            'controller_name' => 'TypeBController',
        ]);
    }

    #[Route('/typesList', name: 'app_typesList', methods: ['GET'])]
    public function typeBList(Request $request, TypeBRepository $typeBRepository): Response
    {
        $form = $this->createForm(TypeBSearchType::class);
        $form->handleRequest($request);

        $searchTerm = $request->query->get('search', '');

        $queryBuilder = $typeBRepository->createQueryBuilder('t');

        if ($form->isSubmitted() && $form->isValid()) {
            $data = $form->getData();

            if (!empty($data['titre'])) {
                $queryBuilder->andWhere('t.libelle LIKE :libelle')
                    ->setParameter('libelle', '%' . $data['libelle'] . '%');
            }

        } elseif ($searchTerm) {
            $queryBuilder->where('t.libelle LIKE :search')
                ->setParameter('search', '%' . $searchTerm . '%');
        }

        $typeB = $queryBuilder->getQuery()->getResult();

        return $this->render('type_b/list.html.twig', [
            'form' => $form->createView(),
            'typeB' => $typeB,
            'searchTerm' => $searchTerm,
        ]);
    }

    #[Route('/addTypes', name: 'addTypes', methods: ['GET', 'POST'])]
    public function addType(Request $request): Response
    {
        $typeB = new TypeB();
        $form = $this->createForm(TypeBType::class, $typeB);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->entityManager->persist($typeB);
            $this->entityManager->flush();

            return $this->redirectToRoute('app_typesList');
        }

        return $this->render('Type_b/add.html.twig', [
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

    #[Route('/type/edit/{id}', name: 'app_editTypes', methods: ['GET', 'POST'])]
    public function editType(Request $request, TypeB $typeB, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TypeBType::class, $typeB);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
    
            return $this->redirectToRoute('app_typesList');
        }
    
        return $this->render('Type_b/edit.html.twig', [
            'form' => $form->createView(),
            'typeB' => $typeB, 
        ]);
    }
    

    #[Route('/type/delete/{id}', name: 'app_deleteTypes', methods: ['GET', 'DELETE'])]
    public function delete(TypeB $typeB): Response
    {
        if ($typeB) {
            $this->entityManager->remove($typeB);
            $this->entityManager->flush();
        }
        return $this->redirectToRoute('app_typesList');
    }
}
