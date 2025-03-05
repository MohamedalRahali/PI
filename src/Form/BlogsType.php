<?php

namespace App\Form;

use App\Entity\Blogs;
use App\Entity\TypeB;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;

class BlogsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre', TextType::class, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Le titre ne peut pas être vide.']),
                    new Assert\Length(['min' => 3, 'minMessage' => 'Le titre doit avoir au moins {{ limit }} caractères.']),
                ],
                'label' => 'Titre du blog',
            ])
            ->add('descr', TextareaType::class, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'La description ne peut pas être vide.']),
                    new Assert\Length(['min' => 10, 'minMessage' => 'La description doit avoir au moins {{ limit }} caractères.']),
                ],
                'label' => 'Description',
            ])
            ->add('datePub', DateTimeType::class, [
                'widget' => 'single_text',
                'label' => 'Date à publier',
            ])
            ->add('typeBs', EntityType::class, [
                'class' => TypeB::class,
                'choice_label' => 'libelle',
                'multiple' => true,
                'expanded' => false,
            ]);
            $builder->addEventListener(FormEvents::SUBMIT, function (FormEvent $event) {
                $data = $event->getData();
                if ($data->getDatePub() < $data->getDateCrea()) {
                    $event->getForm()->get('datePub')->addError(new \Symfony\Component\Form\FormError('La date de publication doit être après la date de création.'));
                }
            });
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Blogs::class,
        ]);
    }
}
