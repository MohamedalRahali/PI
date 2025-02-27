<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class BlogSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre', TextType::class, [
                'required' => false,
                'label' => 'Titre du blog',
            ])
            ->add('descr', TextType::class, [
                'required' => false,
                'label' => 'Description',
            ])
            ->add('dateCrea', DateType::class, [
                'required' => false,
                'label' => 'Date de création',
                'widget' => 'single_text',
            ])
            ->add('typeBs', ChoiceType::class, [
                'required' => false,
                'label' => 'Type',
                'choices' => [
                    'Type 1' => 'type1',
                    'Type 2' => 'type2',
                    // Ajoutez d'autres types selon vos besoins
                ],
                'multiple' => true,
                'expanded' => true,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}